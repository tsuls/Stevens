//
//  main.swift
//  MyServer
//
//  Created by DD on 3/6/18.
//  Copyright © 2018 DD. All rights reserved.
//

import Foundation
import SwiftSocket

func echoService(client: TCPClient) {
    print("Newclient from:\(client.address)[\(client.port)]")
    var d = client.read(1024*10)
    client.send(data: d!)
    client.close()
}

func testServer() {
    let server = TCPServer(address: "127.0.0.1", port: 9091)
    switch server.listen() {
    case .success:
        while true {
            if let client = server.accept() {
                echoService(client: client)
            } else {
                print("accept error")
            }
        }
    case .failure(let error):
        print(error)
    }
}


func testudpserver(){
    DispatchQueue.global(priority: DispatchQueue.GlobalQueuePriority.background).async(execute: { () -> Void in
        let server:UDPServer=UDPServer(address:"127.0.0.1",port:8080)
        let run:Bool=true
        while run{
            var (data,remoteip,remoteport)=server.recv(1024)
            print("recive")
            if let d=data{
                if let str=String(bytes: d, encoding: String.Encoding.utf8){
                    print(str)
                }
            }
            print(remoteip)
            server.close()
            break
        }
    })
}
func testudpclient(){
    let client:UDPClient=UDPClient(address: "localhost", port: 8080)
    print("send hello world")
    client.send(string: "hello world")
    client.close()
}
//testudpBroadcastclient()
func testudpBroadcastserver(){
    DispatchQueue.global(priority: DispatchQueue.GlobalQueuePriority.background).async(execute: { () -> Void in
        //turn the server to broadcast mode with the address 255.255.255.255 or empty string
        let server:UDPServer=UDPServer(address:"",port:8080)
        let run:Bool=true
        print("server.started")
        while run{
            let (data,remoteip,remoteport)=server.recv(1024)
            print("recive\(remoteip);\(remoteport)")
            if let d=data{
                if let str=String(bytes: d, encoding: String.Encoding.utf8){
                    print(str)
                }
            }
            print(remoteip)
        }
        print("server.close")
        server.close()
    })
}
func testudpBroadcastclient(){
    //wait a few second till server will ready
    sleep(2)
    print("Broadcastclient.send...")
    let clientB:UDPClient = UDPClient(address: "255.255.255.255", port: 8080)
    clientB.enableBroadcast()
    clientB.send(string: "test hello from broadcast")
    clientB.close()
}

//testServer()


//testudpserver()
//testudpclient()

//testserver()

testudpBroadcastserver()
testudpBroadcastclient()

var stdinput=FileHandle.standardInput
stdinput.readDataToEndOfFile()


