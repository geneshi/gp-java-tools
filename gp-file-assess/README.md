## What
P2C(PII to Cloud) validator is a Jar based command line to validate the gap of using GP to translate traditional format PII files and provide fix recommendations.
## How
example:
```
java -jar gp-cli.jar assess-file -j credentials_file_path -t file_type -b bundle_name -f to_be_accessed_file_path
```
- operation: assess-file
- -j: json credential file path
- -t: GP supported type
- -b: a prefix of a bundle name
- -f: to be assessed file path
