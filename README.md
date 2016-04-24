# AutoHome
An open source and open hardware home automation project

This project uses a [Particle Photon](https://www.particle.io/prototype#photon) and an Android app to monitor temperature and humidity and turn on/off a lamp in a room.

<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/widget.png"/>

## Hardware

#### Parts list

* 1x [Photon w/ headers](https://store.particle.io/collections/photon)
* 1x [Humidity and temperature sensor RHT03](https://www.sparkfun.com/products/10167)
* 1x [Relay module](https://www.robocore.net/modules.php?name=GR_LojaVirtual&prod=258)
* 1x Pin header 1x8
* 1x Resistor 1k
* 1x Resistor 10k
* 8x F/F jumper wire
* 1x 5V 1A power supply
* 1x micro B USB cable

#### Prototype

<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/prototype.png"/>

#### PCB

PCB Design: [123D Circuits](https://123d.circuits.io/circuits/1929047-autohome)

<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/pcb0.png"/>
<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/pcb1.png"/>
<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/pcb2.png"/>
<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/pcb3.png"/>

#### Installation

<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/installation.png"/>

## Android

Android Studio project: [https://github.com/edu1910/AutoHome/tree/master/android/AutoHome](https://github.com/edu1910/AutoHome/tree/master/android/AutoHome)

You will need to modify the app build.gradle file to add information about your credentials in Particle.

<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/widget_video.gif"/>

## Photon

Photon firmware: [https://github.com/edu1910/AutoHome/tree/master/particle/photon/firmware](https://github.com/edu1910/AutoHome/tree/master/particle/photon/firmware)

Photon uses a webhook to call the API [OneSignal](https://onesignal.com/) and send a push notification to mobile phones informing about changes in the lamp status. Change the [webhook file](https://github.com/edu1910/AutoHome/particle/webhook/onesignal.json) before add it in Particle.

<img src="https://raw.githubusercontent.com/edu1910/AutoHome/master/resources/images/photon_code.png"/>
