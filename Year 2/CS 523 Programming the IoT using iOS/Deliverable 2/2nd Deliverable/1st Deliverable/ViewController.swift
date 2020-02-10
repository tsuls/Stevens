//
//  ViewController.swift
//  Final
//
//  Created by Tyler on 7/11/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import UIKit
import CoreBluetooth

let BodyWeight_CBUUID = CBUUID(string: "0x181D")
let BodyWeiht_Characteristic_CBUUID = CBUUID(string: "0x2A9B")

class ViewController: UIViewController, CBCentralManagerDelegate, CBPeripheralDelegate
{

    @IBOutlet weak var info: UIButton!
    @IBOutlet weak var weightNumber: UILabel!
    @IBOutlet weak var weightLabel: UILabel!
    @IBOutlet weak var tear: UIButton!
    @IBOutlet weak var ounceButton: UIButton!
    @IBOutlet weak var poundButton: UIButton!
    @IBOutlet weak var bodyScaleButton: UIButton!
    
    var centralManager: CBCentralManager?
    var peripheralBodyScale: CBPeripheral?
    var bodyWeight = 0
    
    var menuOpen = false
    var currentUnit = "Grams"
    
    let gramtoOunce:CGFloat = 0.035274
    let gramtoPound:CGFloat = 0.00220462
    
    var tearWeight:CGFloat = 0.0
    var roundGrams:CGFloat = 0.0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
       // bottomConst.constant = 135
        
        //Start BT Scanning
        let centralQueue: DispatchQueue = DispatchQueue(label: "com.iosbrain.centralQueueName", attributes: .concurrent)
        
         centralManager = CBCentralManager(delegate: self, queue: centralQueue)
    
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?)
    {
        if let SecondViewController = segue.destination as? SecondViewController
        {
            SecondViewController.label = weightNumber.text!
            SecondViewController.BTlabel = String(bodyWeight)
        }
    }
    
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func centralManagerDidUpdateState(_ central: CBCentralManager)
    {
        if central.state == .poweredOn
        {
            //Scan for the Service
            centralManager?.scanForPeripherals(withServices: [BodyWeight_CBUUID])
        }
    }
    
    //See what we are connected to
    func centralManager(_ central: CBCentralManager, didDiscover peripheral: CBPeripheral, advertisementData: [String : Any], rssi RSSI: NSNumber)
    {
        print(peripheral.name!)
        decodePeripheralState(peripheralState: peripheral.state)
        
        //Assign to variable
        peripheralBodyScale = peripheral
        peripheralBodyScale?.delegate = self
        
        //Stop scanning unless disconeccted
        centralManager?.stopScan()
        
        //Connect
        centralManager?.connect(peripheralBodyScale!)
        print("CONNECTED")
    }
    
    //Connected
    func centralManager(_ central: CBCentralManager, didConnect peripheral: CBPeripheral)
    {
        //Sucessfully Connected
        print("CONNECTED SUCESSFULLY")
        
        peripheralBodyScale?.discoverServices([BodyWeight_CBUUID])
    }
    
    //Disconnected
    func centralManager(_ central: CBCentralManager, didDisconnectPeripheral peripheral: CBPeripheral, error: Error?)
    {
        centralManager?.scanForPeripherals(withServices: [BodyWeight_CBUUID])
    }
    
    //Look for Services of interest
    func peripheral(_ peripheral: CBPeripheral, didDiscoverServices error: Error?)
    {
        
        for service in peripheral.services!
        {
            
            if service.uuid == BodyWeight_CBUUID
            {
                
                print("Service: \(service)")
                peripheral.discoverCharacteristics(nil, for: service)
            }
            
        }
        
    }

    //Get the characteristic
    func peripheral(_ peripheral: CBPeripheral, didDiscoverCharacteristicsFor service: CBService, error: Error?) {
        
        for characteristic in service.characteristics!
        {
            print(characteristic)
            
            if characteristic.uuid == BodyWeiht_Characteristic_CBUUID
            {
                peripheral.readValue(for: characteristic)
            }
        }
    }
    
    //Check if characteristic updated
    func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor characteristic: CBCharacteristic, error: Error?)
    {
        if characteristic.uuid == BodyWeiht_Characteristic_CBUUID
        {
            //decode
            bodyWeight = getBodyWeight(using: characteristic)
            print("THE WEIGHT IS \(bodyWeight)")
        }
    }
    
    func getBodyWeight(using bodyWeightCharacteristic: CBCharacteristic) -> Int
    {
        let bodyWeightValue = bodyWeightCharacteristic.value!
        let buffer = [UInt8](bodyWeightValue)
        
        if ((buffer[0] & 0x01) == 0)
        {
            //second byte
            print("Weight is in KG")
            return Int(buffer[1])
        }
        else
        {
            print("Weight is in lb")
            return Int(buffer[1])
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?)
    {
        if let touch = touches.first
        {
            if #available(iOS 9.0, *)
            {
                if traitCollection.forceTouchCapability == UIForceTouchCapability.available
                {
                    if touch.force >= touch.maximumPossibleForce
                    {
                        var maxWeight:CGFloat = 0.0
                        
                        if currentUnit == "Pounds"
                        {
                           maxWeight = (385 - tearWeight) * gramtoPound
                            let formattedMaxWeight = String(format: "%.2f", maxWeight)
                             weightNumber.text = "\(formattedMaxWeight)+"
                        }
                        else if currentUnit == "Ounces"
                        {
                           maxWeight = (385 - tearWeight) * gramtoOunce
                            let formattedMaxWeight = String(format: "%.2f", maxWeight)
                             weightNumber.text = "\(formattedMaxWeight)+"
                        }
                        else
                        {
                             maxWeight = (385 - tearWeight)
                             weightNumber.text = "\(maxWeight)+"
                        }
                    }
                    else
                    {
                        let force = touch.force/touch.maximumPossibleForce
                        let grams = force * 385
                       
                        if currentUnit == "Pounds"
                        {
                            roundGrams = (grams - tearWeight) * gramtoPound
                        }
                        else if currentUnit == "Ounces"
                        {
                            roundGrams = (grams - tearWeight) * gramtoOunce
                        }
                        else
                        {
                            roundGrams = grams - tearWeight
                        }
                        
                        let formattedGrams = String(format: "%.2f", roundGrams)
                        weightNumber.text = formattedGrams
                    }
                }
            }
        }
    }
    
    
    @IBAction func pressTear(_ sender: UIButton)
    {
        tearWeight = roundGrams
        let formattedTear = String(format: "%.2f", tearWeight)
        if let n = NumberFormatter().number(from: formattedTear)
        {
            tearWeight = CGFloat(truncating: n)
        }
        roundGrams = 0
        weightNumber.text = "0.0"
    }
    
    @IBAction func pressInfo(_ sender: UIButton)
    {
         let alert = UIAlertController(title: "3D Scale", message: "Place on object on the scale to weigh it. Or connect Bluetooth scale for heavier objects", preferredStyle: .alert)
        
        let dismiss = UIAlertAction(title: "Dismiss", style: .cancel, handler: nil)
        
            alert.addAction(dismiss)
           present(alert, animated: true, completion: nil)
    }
    
    @IBAction func pressPound(_ sender: UIButton)
    {
        let temp =  poundButton.titleLabel?.text
        poundButton.setTitle(currentUnit, for: [])
        currentUnit = temp!
        weightLabel.text = currentUnit + " Weighed"
    }
    
    @IBAction func pressOunce(_ sender: UIButton)
    {
        let temp =  ounceButton.titleLabel?.text
        ounceButton.setTitle(currentUnit, for: [])
        currentUnit = temp!
        weightLabel.text = currentUnit + " Weighed"
    }
    
    @IBAction func onSegueButton(_ sender: Any)
    {
        performSegue(withIdentifier: "switch", sender: self)
    }
    
    @IBAction func pressBodyScale(_ sender: UIButton)
    {
        let options = UIAlertController(title: nil, message: "Weight Recorded: \(bodyWeight)  Grams", preferredStyle: .actionSheet)
        
        let deleteAction = UIAlertAction(title: "Delete", style: .default, handler: {
            (alert: UIAlertAction!) -> Void in
            print("File Deleted")
        })
        
        let saveAction = UIAlertAction(title: "Save", style: .default, handler: {
            (alert: UIAlertAction!) -> Void in
            print("File Saved")
        })
        
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: {
            (alert: UIAlertAction!) -> Void in
            print("Cancelled")
        })
        
        options.addAction(deleteAction)
        options.addAction(saveAction)
        options.addAction(cancelAction)
        
        self.present(options, animated: true, completion: nil)
    
    }
    func decodePeripheralState(peripheralState: CBPeripheralState)
    {
        switch peripheralState
        {
        case .disconnected:
            print("Peripheral state: disconnected")
        case .connected:
            print("Peripheral state: connected")
        case .connecting:
            print("Peripheral state: connecting")
        case .disconnecting:
            print("Peripheral state: disconnecting")
        }
    }
}
