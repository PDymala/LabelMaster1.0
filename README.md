# LabelMaster1.0
Application to check if a label is genuine by checking QR and DM code.

QR code may have any information like a number (e.g. 1234...), REST API url (e.g. http://lab.me/1234) or any other information.

Datamatrix code has to have a cryptographic function of QR code information. In the initial case, MD5- 32 characters. 

After scanning both of them, the application shows if they are complient

For generating a database with numbers/urls and it's hashes, check other application ....


![Image](lm.png)

Sample two codes that are complient:

<img src="qr-code.png" width="200" />


<img src="dm-code.png" width="200" />

And a random dm code if you want to try an non complient code

<img src="https://www.cognex.com/library/media/resources/symbologies/datamatrix.jpg?h=300&w=447&la=en&hash=67F50BAFC5C7C02B92A525BC858B9A7A8660A349" width="300" />



Code scannig thanks to https://github.com/yuriy-budiyev/code-scanner

