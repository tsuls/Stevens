//
//  SecondViewController.swift
//  1st Deliverable
//
//  Created by Tyler Sulsenti on 8/22/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import Foundation
import UIKit

class SecondViewController: UIViewController
{
    
    @IBOutlet weak var touchBTLabel: UILabel!
    @IBOutlet weak var touchLabel: UILabel!
    
    var label = ""
    var BTlabel = ""
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        touchLabel.text = label + " Grams"
        touchBTLabel.text = BTlabel + " Grams"
        
    }
   
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
