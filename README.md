# Initial configuration for the android sdk.

## Update your project plugins

In your settings.gradle file, add rules to include the Android Gradle plugin. Verify that you also have Google's Maven repository, plus include the local libs path.

```
pluginManagement {

	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()

		flatDir {
			dirs 'libs'
		}
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

	repositories {
		google()
		mavenCentral()

		flatDir {
			dirs 'libs'
		}
	}
}

```

## Add the Cryptobucks SDK to your app.

In your module (application layer) Gradle file (usually app/build.gradle), add the dependencies needed for the sdk to work.

```

dependencies {

	implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')

	//…

	// Cryptobucks Sdk
	debugImplementation files('libs/cryptobucks-sdk-release.aar')

	// Retrofit
	implementation 'com.squareup.retrofit2:retrofit:2.9.0'
	implementation 'com.squareup.retrofit2:converter-moshi:2.9.0
	implementation 'com.squareup.moshi:moshi-kotlin:1.12.0'
	implementation 'com.squareup.retrofit2:adapter-rxjava2:2.9.0'
	implementation 'com.squareup.moshi:moshi:1.12.0'
	kapt 'com.squareup.moshi:moshi-kotlin-codegen:1.12.0'
	implementation 'com.squareup.retrofit2:converter-gson:2.6.2'

	//Coroutine Lifecycle Scopes
	implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
	implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'

}

```
> Note: All dependencies presented here are required for the SDK to function.

## Create an ApiKey configuration for the SDK.

### You must create a CryptobucksPaySdk instance by passing it the supplied apiKey to pull up the SDK.

The cryptobucks SDK runs separately in an Activity within your app. To return the result to your application, it supports the startActivityForResult APIs.

```

private val cryptobucksSdk = registerForActivityResult(CryptobucksPaySdk("apiKey")) { result ->

	when (result ) {
		is Success -> {
			//…
		}

		is Error -> {
			//…
		}
	}
}

```

### Start the SDK

```

private fun createRequest() {

	cryptobucksSdk.launch(
		InvoiceRequest(
			taxRate = 0.0,
			customerPhone = customerPhone,
			amount = amount,
			customerEmail = customerEmail,
			businessName = businessName,
			description = description,
			tipRate = 0.0,
			customerName = customerName
		)
	)
}

```

To start the SDK it is necessary to provide an invoiceRequest object, it consists of:

|    taxRate    | Double |                                |
| customerPhone | String |         Customer phone         |
|    amount     | Double |       Transaction amount       |
| customerEmail | String |         Customer mail          |
| businessName  | String |          Business name         |
| customerName  | String |         Customer name          |
|  description  | String | Description of the transaction |
|    tipRate    | Double |                                |

At this point CryptobucksSdk will open and activate the corresponding calls according to how the process ended. This gives the results in the Success and Error callbacks. Giving a parameter as different for each case.

The on Success method returns a SuccessInvoices object.

|    amount          | Double  |        Transaction value       |
| baseAmount         | Double  | Base value of the transaction  |
| businessName       | String  |         Business name          |
| checkoutUrl        | String  |                                |
| createdAt          | String  |         Creation date          |
| customerEmail      | String  |         Customer mail          |
| customerName       | String  |         Customer name          |
| customerPhone      | String  |         Customer phone         |
|  description       | String  | Description of the transaction |
|    expiresAt       | String  |                                |
|    expiresIn       | String  |                                |
|    id              | String  |                                |
|     identifier     | String  |                                |
|     paidAmount     | Double  |                                |
|    settleAmount    | Double  |                                |
|    status          | Status  |       Transaction status       |
|    tax             | Double  |              Tax               |
|    taxRate         | Double  |                                |
|    tip             | Double  |                                |
|    tipRate         | Double  |                                |
|   transactionFee   | Double  |                                |
| transactionFeeRate | Double  |                                |
| underpaid          | Boolean |                                |

### status enum

```
Status {
    PENDING, 
	PROCESSING, 
	PAID, 
	COMPLETED, 
	EXPIRED, 
	CANCELLED, 
	ERROR,
	ISSUE
}

```

And the Error method returns a `string` indicating the error returned by the SDK.