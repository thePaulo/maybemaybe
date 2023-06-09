import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import {QRCodeModule} from 'angularx-qrcode';
import {  HttpClientModule } from '@angular/common/http';
import { AsyncLoginService } from './main/async-login.service';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    QRCodeModule,
    HttpClientModule
  ],
  providers: [AsyncLoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
