# Belong Coding Challenge!
Create a specification & Implementation for 3 APIs:
1. Get all phone numbers
2. Get all phone numbers for a single customer
3. Activate a customers given phone number
## Requirements
- 1 Customer can have many phone numbers
- The customers data already exists and is accessible
## Assumptions
- Security and authorisation is handled elsewhere / out of scope
- Multiple customers can have the same number (e.g. A Grandmother would like her son to manage her account whilt he is also a customer)
- Customers are identified by GUIDs in and around this service
- "Get all phone numbers" would like to get duplicates
- The GET api's are just wanting the phone numbers, not their statuses nor customer associations
- The Database is not so large as to require things such as pagination on GETs
## Points to consider
- This looks like its to support contact information display so ill assume its for that.
- Each number can be active or inactive but new status may come in the future.
- Phone numbers aren't always just numbers!


# Specification
I'll just pretend that localhost:8080 is the domain name.
## GET:  http://localhost:8080/getAllNumbers
**Summary:** This returns a list of all phone numbers saved in our system.

**Responses:**

| Response Code | Body | Comment |
|--|--|--|
| 200 | [phonenumber1, phonenumber2,...] | This is the standard success response |
|500||Internal Server Error|

## GET:  http://localhost:8080/getAllNumbers/{customerId}
**Summary:** This returns a list of all phone numbers for a specific customer.

**Parameters:**
| Parameter | Description |
|--|--|
| customerId | This is the unique identifer for a customer. Must be a GUID |

**Responses:**

| Response Code | Body | Comment |
|--|--|--|
| 200 | [phonenumber1, phonenumber2,...] | This is the standard success response |
|400|[error message]|Bad Request. The customerId given is invalid|
|500||Internal Server Error|

## POST:  http://localhost:8080/activate
**Summary:** This returns a list of all phone numbers for a specific customer.

**Body Parameters:**
| Parameter | Description |
|--|--|
| customerId | This is the unique identifer for a customer. Must be a GUID |
| phoneNumber | The customers phone number to be activated. Cannot contain letters |
Example:
```json
{
  "customerId": "ca66ffa1-1082-47f5-93fa-140528587759",
  "phoneNumber ": "0800838383"
}
```

**Responses:**

| Response Code | Body | Comment |
|--|--|--|
| 200 |  | This is the standard success response |
|400|[parameter1, parameter2]|Bad Request. Response body shows invalid fields.|
|500||Internal Server Error|
