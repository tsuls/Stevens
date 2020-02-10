//
//  SecondViewController.swift
//  Tic Tac Toe
//
//  Created by Tyler on 7/10/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController
{

    var activePLayer = 1 //X, 2 = O
    var gameState = [0,0,0,0,0,0,0,0,0]
    var winner:String = ""
    var player1 = ""
    var player2 = ""
    
    let winningCombinations = [[0,1,2], [3,4,5], [6,7,8], [0,3,6], [1,4,7], [2,5,8], [0,4,8], [2,4,6]]
    
    var active = true
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    override func prepare(for segue: UIStoryboardSegue, sender: Any?)
    {
        if let ViewController = segue.destination as? ViewController
        {
            ViewController.winner = winner
        }
    }
    @IBAction func squarePressed(_ sender: UIButton)
    {
        if(gameState[sender.tag-1] == 0 && active == true)
        {
            gameState[sender.tag-1] = activePLayer
            
            if(activePLayer == 1)
            {
                sender.setImage(UIImage(named: "Cross.png"), for: UIControlState())
                activePLayer = 2
            }
            else
            {
                sender.setImage(UIImage(named: "Nought.png"), for: UIControlState())
                activePLayer = 1
            }
        }
        
        for combination in winningCombinations
        {
            if gameState[combination[0]] != 0 && gameState[combination[0]] == gameState[combination[1]] && gameState[combination[1]] == gameState[combination[2]]
            {
                active = false
                if gameState[combination[0]] == 1
                {
                   winner = player1
                   performSegue(withIdentifier: "switchBack", sender: self)
                }
                else
                {
                    winner = player2
                    performSegue(withIdentifier: "switchBack", sender: self)
                }
            }
        }
        active = false
        
        for i in gameState
        {
            if i == 0
            {
                active = true
                break
            }
        }
        
        if(active == false)
        {
            winner = "Draw"
            performSegue(withIdentifier: "switchBack", sender: self)
        }
        
    }
}
