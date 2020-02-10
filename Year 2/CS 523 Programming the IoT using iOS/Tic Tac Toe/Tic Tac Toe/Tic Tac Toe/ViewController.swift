//
//  ViewController.swift
//  Tic Tac Toe
//
//  Created by Tyler on 7/10/18.
//  Copyright © 2018 Tyler. All rights reserved.
///Users/Tyler/Google Drive/Stevens/CS 529 iOS/Tic Tac Toe/Tic Tac Toe/Tic Tac Toe/Base.lproj/Main.storyboard

import UIKit

class ViewController: UIViewController
{
    var player1:String = ""
    var player2:String = ""
    var winner:String = ""
    
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        if(winner != "")
        {
            if(winner == "Draw")
            {
                MainLabel.text = "Its a " + winner + "!"
            }
            else
            {
                MainLabel.text = winner + " Wins!"
            }
        }
        
    }

    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?)
    {
         if let SecondViewController = segue.destination as? SecondViewController
         {
            SecondViewController.player1 = player1
            SecondViewController.player2 = player2
        }
    }
    
    @IBAction func onSegueButton(_ sender: UIButton)
    {
        player1 = P1Box.text!
        player2 = P2Box.text!
        performSegue(withIdentifier: "switchScene", sender: self)
    }
    
    @IBOutlet weak var MainLabel: UILabel!
    @IBOutlet weak var SecondaryLabel: UILabel!
    @IBOutlet weak var P1Label: UILabel!
    @IBOutlet weak var P2Label: UILabel!
    @IBOutlet weak var P1Box: UITextField!
    @IBOutlet weak var P2Box: UITextField!
    @IBOutlet weak var PlayButton: UIButton!
    
    
    
    
}

