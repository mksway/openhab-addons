# LGEssEnervu Binding

This binding is meant to retrieve data of the LG Ess Powerrouter/Battery bundle.
It is designed to retrieve data either from the lg cloud (https://enervu.lg-ess.com) or via WLAN directly from the local device.

## Supported Things

The following thing types are supported:

|Thing                |ID                         |Description                 |
|---------------------|---------------------------|----------------------------|
|Powerrouter|powerrouter            |Represents the physical powerrouter & battery device         |


## Discovery

Once the binding is initialized it will look for devices on the local network.
If a scan is started manually and a device could not be found just try again. From time to time the
device does not respond on the first try.


## Getting started

### Obtaining the password for accessing the device via local network.
In order to get the device password you need to be within the wifi provided by the box.
Once you are connected to the wifi of your device send a json request to
`https://192.168.23.1/v1/user/setting/read/password`
 with the following payload 
`{"key": "lgepmsuser!@#"}`

## Thing Configuration

| Parameter            |              Type                 | Required/Optional | Group        |Description|
|---------------------|---------------------------|----------------------------|--------|--|
|dataSourceCloud|boolean|required|general |Select the datasource where to retrieve data from
|timeout|integer|optional|general|The timeout in seconds
|kwhPrice|decimal|optional|general| Price of the kWh paid to local provider
|kwhPriceSell|decimal|optional|general| Price local provider pays for selling power
|co2Factor|decimal|optional| general | Factor of the powermix (e.g. 0.71% of bought power is from green energy
|refreshInterval | integer |optional | local | How often shall data be polled (in seconds)
|hostName | text | required if source is lan |local| hostname/ip of local device
|passwordLocal | text | required if source is lan | local | password of the local device
|user | text | required if source is cloud | cloud |username / email used for login at https://enervu.lg-ess.com
|passwordCloud | text| required if source is cloud|cloud|password used for login at https://enervu.lg-ess.com
|refreshIntervalCloud | integer |optional | cloud | How often shall data be polled (in seconds)




## Channels

_Here you should provide information about available channel types, what their meaning is and how they can be used._

_Note that it is planned to generate some part of this based on the XML files within ```src/main/resources/OH-INF/thing``` of your binding._

| channel  | type   | description                  |
|----------|--------|------------------------------|
| currentPowerFromGrid| Number:Power| Current power taken from grid  |
| dailyPowerFromGrid| Number:Power| Daily power taken from grid |
| monthlyPowerFromGrid| Number:Power| Monthly power taken from grid  |
| currentPowerToGrid| Number:Power| Current power provided to grid  |
| dailyPowerToGrid| Number:Power| Daily power provided to grid  |
| monthlyPowerToGrid| Number:Power| Monthly power provided to grid   |
| currentPowerFromPV| Number:Power| Current power produced by PV  |
| dailyPowerFromPV| Number:Power| Daily power produced by PV  |
| monthlyPowerFromPV| Number:Power| Monthly power produced by PV |
| batterySoc| Number:Power| Current battery SOC state  |
| batteryStatus| String | Current battery operating mode (idle, charging,discharging)  |
| batteryWintermode| Switch| Status of wintermode  |
| currentPowerChargingToBattery| Number:Power| Current power used to charge the battery  |
| dailyBatteryCharge| Number:Power| Daily power used to charge the battery |
| monthlyBatteryCharge| Number:Power| Monthly power used to charge the battery  |
| currentPowerDischargingFromBattery| Number:Power| Current power used to discharge the battery   |
| dailyPowerDischargingFromBattery| Number:Power| Daily power used to discharge the battery  |
| monthlyPowerDischargingFromBattery| Number:Power| Monthly power used to discharge the battery  |
| currentTotalPowerConsumption| Number:Power| Current total power consumption (grid+battery+pv) |
| dailyTotalPowerConsumption| Number:Power| Daily total power consumption (grid+battery+pv)  |
| monthlyTotalPowerConsumption| Number:Power| Monthly total power consumption (grid+battery+pv)  |
| currentDirectPowerConsumption| Number:Power| Current direct power consumption from PV  |
| dailyDirectPowerConsumption| Number:Power| Daily direct power consumption from PV  |
| monthlyDirectPowerConsumption| Number:Power| Monthly direct power consumption from PV  |
| isDirectConsuming| Switch | Is PV power currently directly consumed?  |
| isBatteryCharging| Switch | Is the battery currently charging?  |
| isBatteryDischarging| Switch | Is the battery currently discharging?  |
| isGridSelling| Switch | Currently selling power to grid?  |
| isGridBuying| Switch | Currently buying power from grid?  |
| isChargingFromGrid| Switch | Is battery currently charging from grid?  |
| monthlyCO2Savings| Number| Saved CO<sub>2</sub> by consuming power from PV/Battery rather than from grid  |
| monthlyEarnings| Number| Money earned by selling power to local energy provider in current month  |
| monthlyPaid| Number| Money paid to local energy provider in current month  |
| monthlyMoneySavings| Number| Saved money by selling power rather than buying in current month  |


