 //
//  ViewController.swift
//  Web Browser
//
//  Created by Tyler on 6/17/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import UIKit
import WebKit

class ViewController: UIViewController, UITextFieldDelegate, WKNavigationDelegate
{

    
    @IBOutlet weak var webView: WKWebView!
    
    @IBOutlet weak var backButton: UIButton!
    
    @IBOutlet weak var forwardButton: UIButton!
    
    @IBOutlet weak var urlTextField: UITextField!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        webView.navigationDelegate = self
    }
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!)
    {
        backButton.isEnabled = webView.canGoBack
        forwardButton.isEnabled = webView.canGoForward
        urlTextField.text = webView.url?.absoluteString
    }
    
    override func viewWillAppear(_ animated: Bool)
    {
        super.viewWillAppear(animated)
        
        let urlString:String = "http://www.apple.com"
        let url:URL = URL(string: urlString)!
        let urlRequest:URLRequest = URLRequest(url: url)
        
        webView.load(urlRequest)
        urlTextField.text = urlString
    }
    
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool
    {
        let urlString:String = urlTextField.text!
        let url:URL = URL(string: urlString)!
        let urlRequest:URLRequest = URLRequest(url: url)
        
        webView.load(urlRequest)
        urlTextField.text = urlString
        textField.resignFirstResponder()
        
        return true
    }

    @IBAction func backButtonPressed(_ sender: UIButton)
    {
        if webView.canGoBack
        {
            webView.goBack()
        }
    }
    
    @IBAction func fowardButtonPressed(_ sender: UIButton)
    {
        if webView.canGoForward
        {
            webView.goForward()
        }
    }
}

