#include <SoftwareSerial.h>
SoftwareSerial BT(10,11); //10 RX, 11 TX.

//Definimos los pin donde se va a conectar el sensor  
const int trigPin = 5;  
const int echoPin = 6;  
   
// definimos las variables  
long duration;  
int distance;  
    
void setup() {  
pinMode(trigPin, OUTPUT); // Establecemos el pin 9 como salida  
pinMode(echoPin, INPUT); // y el pin 10 como entrada  
Serial.begin(115200); // Iniciamos el puerto serie
BT.begin(115200);
}  
  
void loop() {  
// Limpiamos el puerto trigPin  
digitalWrite(trigPin, LOW);  
delayMicroseconds(2);  
   
// Ponemos el pin correspondiente al trigPin en alto durante 10 ms  
digitalWrite(trigPin, HIGH);  
delayMicroseconds(10);  
digitalWrite(trigPin, LOW);  
  
// Leemos echoPin, lo cual los devuelve la onda en microsegundos  
duration = pulseIn(echoPin, HIGH);  
   
//Calculamos la distacia  
distance= duration*0.034/2; 
bool activated=false;
long time;
// Y finalmente la mostramos en el monitor serie  
Serial.print("Distancia: ");  
Serial.println(distance);  
if(BT.available()){
  if(distance<70){
    activated=true;
    time=millis();
    while(activated){
      if(millis()-time<5000)
        BT.print("s");
      else{
        activated=false;
        BT.print("h");
      }
    }
  }
  else{
    BT.print("h");
  }
}
delay(200);
}  
