import { Component, OnInit } from '@angular/core';
import { AsyncLoginService } from './async-login.service';
import { webSocket} from 'rxjs/webSocket';
//import Stomp from 'stompjs';
import {Client,Message} from '@stomp/stompjs';
//import * as SockJS from 'sockjs-client';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  public myText: string = 'lool';
  constructor(private service:AsyncLoginService) { 
    //console.log(this.service.createAsyncLogin());
    console.log("espaco");
    console.log(this.service.subscribe("/topic/vehicle"));

    /*var ss = webSocket('ws://localhost:9000/stompendpoint');
    ss.subscribe();
    ss.next("mEu TeSte");
    ss.complete();
    */
    
    /*
    //var socket = new WebSocket('ws://localhost:9000/app/stompendpoint/websocket');
    var socket = new SockJS('/stompendpoint');
    var stompClient = Stomp.over(socket);
    console.log(stompClient);
    stompClient.connect({}, function(frame:any) {
      //setConnected(true);
      console.log('Connected: ' + frame);
      stompClient.subscribe('/queue/reply/${pageContext.session.id}', function(greeting) {
        console.log(greeting.body);  
        //showGreeting(greeting.body);
      });
  });
  */

  }

  ngOnInit(): void {
    
  }

}
