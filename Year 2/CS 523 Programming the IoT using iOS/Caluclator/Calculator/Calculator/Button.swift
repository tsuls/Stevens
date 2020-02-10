//
//  Button.swift
//  Calculator
//
//  Created by Tyler on 6/9/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import UIKit

@IBDesignable
class Button: UIButton
{

    @IBInspectable var button:Bool = false
    {
        didSet
        {
            if button
            {
                layer.cornerRadius = frame.height / 2;
            }
        }
    }
    
    override func prepareForInterfaceBuilder()
    {
        if button
        {
            layer.cornerRadius = frame.height / 2;
        }
    }
}
