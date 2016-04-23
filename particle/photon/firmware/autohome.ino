#include "SparkFunRHT03/SparkFunRHT03.h"

SYSTEM_THREAD(ENABLED);

/********************/
/* GLOBAL CONSTANTS */
/********************/

const int INT_STATUS_PIN = D3;
const int RELAY_PIN      = D5;
const int RHT03_DATA_PIN = D6;

/********************/
/* GLOBAL VARIABLES */
/********************/

double lastHumidity    = 0;
double lastTemperature = 0;

int lastIntStatus = 0;
int lampOn        = 0;

RHT03 rht;

/********************/
/* PUBLIC FUNCTIONS */
/********************/

void setup()
{
    pinMode(RELAY_PIN, OUTPUT);
    pinMode(INT_STATUS_PIN, INPUT);
    
    lastIntStatus = digitalRead(INT_STATUS_PIN);
    
    rht.begin(RHT03_DATA_PIN);
    
    Particle.variable("humi", &lastHumidity, DOUBLE);
    Particle.variable("temp", &lastTemperature, DOUBLE);
    Particle.variable("lamp", &lampOn, INT);
    
    Particle.function("lampToggle", lampToggle);
    
    _notifyLampStatus();
}

void loop()
{
    if (rht.update())
    {
        double currentHumidity = (int) (rht.humidity() * 10) / 10.0;
        double currentTemperature = (int) (rht.tempC() * 10) / 10.0;
        
        if (currentHumidity != lastHumidity || currentTemperature != lastTemperature)
        {
            lastHumidity = currentHumidity;
            lastTemperature = currentTemperature;
        }
    }
    
    if (digitalRead(INT_STATUS_PIN) != lastIntStatus)
    {
        lastIntStatus = !lastIntStatus;
        _changeLampStatus();
    }
    
    delay(50);
}

int lampToggle(String command)
{
    _changeLampStatus();
    return lampOn;
}

/********************/
/* LOCAL FUNCTIONS  */
/********************/

void _notifyLampStatus()
{
    if (Particle.connected())
        Particle.publish("lamp_status", String::format("{\"lamp_status\": %d, \"time\": %lu}", lampOn, Time.now()));
}

void _changeLampStatus()
{
    lampOn = !lampOn;
    _updateRelayState();
    _notifyLampStatus();
}

void _updateRelayState()
{
    digitalWrite(RELAY_PIN, lampOn ? HIGH : LOW);
}

