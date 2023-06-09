import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { first } from 'rxjs';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class AsyncLoginService {

  constructor(private httpClient:HttpClient,private router: Router) { 
    //this.createAsyncLogin();
  }

  createAsyncLogin(){

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    return this.httpClient.get<String>("http://localhost:9000/vinculos/cpf/84833783452",{headers:headers}).subscribe(
      {
        next: (u:any)=>{
          console.log(u);
        }
      }
    );
  }

  socket = new SockJS('http://localhost:9000/stompendpoint');
  stompClient = Stomp.over(this.socket);

  subscribe(topic: string):void{
    const connected : boolean = this.stompClient.connected;
    if(connected){
      this.subscribeTopic(topic);
      return;
    }

    this.stompClient.connect({},():any=>{
      console.log("aquiii");
      this.subscribeTopic(topic);
    });
  }

  subscribeTopic(topic:string):void{
    this.stompClient.subscribe(topic);
  }
}
