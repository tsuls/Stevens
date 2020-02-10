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
    
    @IBOutlet weak var touchLabel: UILabel!
    var label = ""
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        touchLabel.text = label + " Grams"
    }
   
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
