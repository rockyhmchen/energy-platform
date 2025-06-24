# Front-end Dashboard

This is the energy dashboard. 

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.


* Build a docker image
  ```shell
  docker build -t zendoenergy.com/front-end:0.0.1 .
  ```

* Run the docker image
  ```shell
  docker run --rm -it -p 3000:3000 zendoenergy.com/front-end:0.0.1
  ```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.


### Environment variables

- `NEXT_PUBLIC_TO_CALL_BACK_END`: To actually sending the API request to the back-end service.  Default value: `false`
