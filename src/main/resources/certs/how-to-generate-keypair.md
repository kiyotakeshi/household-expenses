```shell
$ openssl genrsa -out keypair.pem 2048

$ openssl rsa -in keypair.pem -pubout -out public.pem

$ openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

※pem file は git 管理しないように設定ずみ

```shell
$ grep "pem" ~/.gitignore_global
*.pem
```
