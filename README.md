# kafka-spring-java-exemplos

Exemplo de Requisição para o endpoint POST /produzir:

```json
{
  "header": {
   "specversion":"0.1",
   "type":"br.com.itau.evento_ted_processada",
   "source":"",
   "id":"UUID",
   "time":"2017-10-31T01:00:00Z",
   "messageversion":"1",
   "transactionid":"",
   "correlationid":"",
   "datacontenttype":"application/avro"
  },
  "payload": {
   "data": {
      "codigo_produto_operacional":100,
      "data_hora_operacao":"2017-10-31T01:00:00Z",
      "codigo_empresa":341,
      "valor":1.0
   }
  },
  "schema": {
   "type":"record",
   "name":"br.com.itau.evento_ted_processada.EventoTedProcessada",
   "fields":[
      {
         "name":"data",
         "type":{
            "type":"record",
            "name":"br.com.itau.evento_ted_processada.EventoTedProcessadaData",
            "fields":[
               {
                  "name":"codigo_produto_operacional",
                  "type":"int"
               },
               {
                  "name":"data_hora_operacao",
                  "type":"string"
               },
               {
                  "name":"codigo_empresa",
                  "type":"int"
               },
               {
                  "name":"valor",
                  "type":"double"
               }
            ]
         }
      }
   ]
  },
  "topico": "processamento-ted"
}
```