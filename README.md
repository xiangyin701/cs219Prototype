# cs219Prototype
This is an example prototype of Device Delegation for my CS219 final project

It contains three components: 

DelegationLayer: application layer that handles how endpoint calls from smart home apps are handled (which delegator to use, authentication, logging and secondary user authorization). 

Delegator: a "virtual device" that can access configured physical device endpoints, delegator handles the endpoint call from applications and distribute them to devices under its management that exposes the corresponding endpoint.

Device: represents the physical device that serves as the terminal for smart home applications. Devices only expose configured endpoints to delegators and delegators do not have access to unknown/unexposed endpoints.
