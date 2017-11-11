
kubectl create -f deployment.yaml
kubectl create -f ingress.yaml
kubectl expose deployment vertx-demo

Point your browser to [http://127.0.0.1:8666/hello](http://127.0.0.1:8666/hello) and enjoy :)
