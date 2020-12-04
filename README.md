#Checkout

##Summary
This Application allows a User to add Items to a Basket which have been loaded into the Inventory. They can add as many Items as they wish, but they must exist in Inventory. The User can Checkout these Items at any time. Upon Checkout, the Application will apply any Promotions in the System to the Items and/or price, and then return the final calculated price.

##Running the Application
The Application has been built with Gradle.

To Start the Application run the following from the root:
```shell script
gradlew bootRun
```

To run the Tests, they can be run from root as well:
```shell script
gradlew clean test
```
Alternatively, they can be run through the IDE of choice. Please refer to the relevant IDE Documentation for how to do this.

Reports are generated automatically as part of a run. These can be viewed under:

```text
/build/reports/tests/test/index.html
```

##Using the Application

Once the Application has been started, it can be accessed on Local Port 8080.
The following APIs are available:

###Inventory:
```text
/inventory
```

This is a GET call, which will return the current Inventory of Items as a JSON List.

###Scan:
```text
/scan
```

This is a POST, which will add an Item to the Basket of the latest Session.
The API accepts a Product Code of an Item currently in the Inventory. The following is an example Request:
```json
{
  "productCode": 1
}
```

It will return a String "success" if successful.

###Basket:
```text
/basket
```

This is a GET call, which will return the current Basket of Items as a JSON List.

###Checkout:
```text
/checkout
```

This is a GET call, which will return the Total Price of all Items in the Basket as a String.
Promotions which are active in the Checkout System will be applied automatically.
The Basket will be cleared out after each call to this API.
The following is an example response:
```text
Total Price: £21.35
```

##Candidate Comments

####Application Structure:
SpringBoot offers a means of building an Application quickly, which allowed me to focus more on Implementation and less on Infrastructure.

I tried to supply APIs which would be most appropriate for the User. In order to Scan Items, they would need to be able to see an Inventory. And they would also likely wish to see their current Basket.

Further Mappings could be implemented in future, such as Clearing out the Basket, Deleting a single Item from the Basket, and Adding an Item to the Inventory.

There is currently no concept of multiple Users. This current limitation means that the Basket needs to be cleared after each Checkout call, and there is also a lack of any user security. These features would be the next step, and would likely need a Database Implementation.

Promotions are currently applied only on Checkout. This is a Business question, and it could be that Users would like to see their Promotions at any time, such as when they Get their Basket; this would need to be clarified with a Product Owner.

####Test Structure:
Unit Tests utilise Mockito to Mock the required services and methods. The Unit Tests have been designed to Mock out everything except for the specific method under Test; even other methods within the same Class have been mocked out through the use of Spies, to have better control over what is being tested and to find where the pain points might lie.

Integration Tests utilise features of SpringBoot to easily. run the entire Application during the Test. Through this, the APIs can be hit directly, simulating an external call more realistically, which offers more reliable testing, and tests the whole Integration of the Application.

####Code Coverage:
Code Coverage has been checked to ensure good quality testing. The Tests currently have over 90% Code Coverage.

The CheckoutApplication Class is not covered, and it is felt that such a class is not appropriate for testing at this level. Should this Class have a problem, the Application would likely fail to even Build, thus exposing this issue early on.

There are some areas in Model which are not tested. These are all not currently used in the Codebase though.

####E2E Tests:
Going forward, this Application should be covered as part of a suite of E2E Tests. Ideally this would be part of a Test Environment in the Deployment Pipeline, whereupon such Tests would run as part of that Deployment Process.

The E2E Tests should be implemented as part of their own Codebase, being independent of the individual Micro-service Implementations. Frameworks to consider are RestAssured for the Implementation, and Cucumber/Gherkin for the Structure.