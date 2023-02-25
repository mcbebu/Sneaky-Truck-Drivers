# NinjaSlot

### Our idea
To implement a scheduling functionality into NinjaChat on Telegram. What is the benefit of this, you may ask?

 Driver | Consignee | NinjaVan 
---|---|---
They get commission from completing these priority deliveries | They know with more certainty when their parcels will be delivered | Getting a cut from the service means a richer ninja!

### Features
- Sends a message to the consignees to inform them of the option to select time slots

    >In addition to the current message to allow consignees to add instructions for drop-off, they will now receive a message informing them of the option the select a time slot for the parcel to be delivered. This message will be sent once the parcel has been scanned and inbounded.
    >They will also be informed of the cutoff time of 0830, after which they will no longer be able to select any time slot.

- Option for consignee to select time slots

    > 4 time slots will be available:
    0900-1200, 1200-1500, 1500-1800, 1800-2000

    Dynamic Pricing based on demand and supply factors, subject to a floor price.

    A time slot option will no longer be available once the capacity has been reached (e.g. 1200-1500 option will no longer show on telegram once capacity has been reached).

- Option to cancel **before** cutoff time

    >Consignees would be able to cancel their selection of any time slot before the cutoff time.

- Payments

    >After the desired time slot has been selected, the bot will generate a link to an external site for payments (using Stripe in our implementation), but for actual implementation perhaps Ninja Finance can be adjusted to support this functionality.
