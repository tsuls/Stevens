//
//  ViewController.swift
//  1st Deliverable
//
//  Created by Tyler on 7/11/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var info: UIButton!
    @IBOutlet weak var settings: UIButton!
    @IBOutlet weak var bottomConst: NSLayoutConstraint!
    @IBOutlet weak var weightNumber: UILabel!
    @IBOutlet weak var weightLabel: UILabel!
    @IBOutlet weak var tear: UIButton!
    @IBOutlet weak var menuView: UIView!
    @IBOutlet weak var ounceButton: UIButton!
    @IBOutlet weak var poundButton: UIButton!
    
    var menuOpen = false
    var currentUnit = "Grams"
    
    let gramtoOunce:CGFloat = 0.035274
    let gramtoPound:CGFloat = 0.00220462
    
    var tearWeight:CGFloat = 0.0
    var roundGrams:CGFloat = 0.0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        bottomConst.constant = 62
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
    

    @IBAction func PressGear(_ sender: UIButton)
    {
        if menuOpen
        {
            bottomConst.constant = 62
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        }
        else
        {
            bottomConst.constant = 0
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        }
        menuOpen = !menuOpen
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
         let alert = UIAlertController(title: "3D Scale", message: "Place on object on the scale to weigh it.", preferredStyle: .alert)
        
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
}

