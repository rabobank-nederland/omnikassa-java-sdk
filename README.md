# Rabo Omnikassa Java SDK

This repository contains the official Java SDK for [Rabo OmniKassa](https://www.rabobank.nl/omnikassa).

Rabo Omnikassa offers merchants an all-in-one solution to receive payments on your physical and online locations. It includes a dashboard that puts you in full control of your Rabo OmniKassa and all products included in it: Rabo OnlineKassa, payment terminals, Rabo PinBox, Rabo SmartPin, Retourpinnen, Rabo PinTegoed, Rabo Betaalverzoek Plus and payment brands such as: Maestro, V PAY, iDEAL, MasterCard, Visa, PayPal, AfterPay and Sofort.

The Java SDK allows Java developers to integrate their web shop with Rabo OmniKassa to handle online payments. Note that besides using an SDK Rabo OmniKassa also provides other ways to integrate that may require less effort. More information on this topic can be found on the [Developer Portal](https://developer.rabobank.nl/overview/rabo-omnikassa) of Rabobank.

To download the SDK go the [Releases section](https://github.com/rabobank-nederland/omnikassa-java-sdk/releases). Download the appropriate release. When opening the ZIP file you can find the SDK jar file as well as additional jar files that are required.

Detailed developer documentation on how to use the Java SDK as well as contact information can be found in the [SDK manual](https://github.com/rabobank-nederland/omnikassa-sdk-doc/blob/main/README.md).

## Release notes

### Version 1.10.0
* Extended SDK to retrieve a list of iDEAL issuers.
* Extended SDK to allow the iDEAL issuer to be passed in the order announcement.
* Extended SDK to allow the payment result page (also known as the 'thank-you' page) to be skipped in the payment process.
* Extended SDK to allow the name of the customer to be passed in the order announcement.
