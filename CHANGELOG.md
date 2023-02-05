# Changelog

## Version 0.6.1
_2023.02.05_
### Fixed
* Fixed crash on missed message arguments

## Version 0.6.0
_2022.08.21_
### New
* Implemented possibility to perform logging output of messages ignoring additional data configuration
### Fixed
* Fixed complex formatter delegate interface to have ability to use other formatter delegates in the formatter delegate implementation

## Version 0.5.0
_2022.06.19_
### New
* Implemented possibility to perform logging output to file on device storage
* Implemented complex formatter delegate support to use other formatter delegates in the formatter delegate implementation
### Fixed
* Fixed logging message split in case of exceeding the maximal logging message length

## Version 0.4.0
_2019.07.19_
### New
* Added methods for logging byte arrays in hexadecimal string representation
### Fixed
* Fixed JSON/XML minimal logging level check
* Prevented shirinking of logging message in case of exceeding the maximal logging message length

## Version 0.3.0
_2017.02.09_
### New
* Added pretty logging formatters for arrays, collections, maps
* Implemented support of custom user-defined logging formatters for class instances
* Added methods for logging single object

## Version 0.2.0
_2016.11.13_
### New
* Added non-parametrized methods to send empty logging mesages for all logging levels

## Version 0.1.0
_2016.11.03_
### New
* Initial release