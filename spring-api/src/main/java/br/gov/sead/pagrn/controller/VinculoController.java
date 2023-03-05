package br.gov.sead.pagrn.controller;

import br.gov.sead.pagrn.dto.vinculo.VinculoDtoResponse;
import br.gov.sead.pagrn.service.SocketService;
import br.gov.sead.pagrn.service.VinculoService;
import com.rabbitmq.client.*;
import org.apache.tomcat.jni.Time;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/vinculos")
@CrossOrigin(origins="*")
public class VinculoController {

    private final ModelMapper modelMapper;

    private final VinculoService service;

    @Autowired
    SocketService socketService;

    public VinculoController(VinculoService service){
        this.service = service;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Método reponsável por listar todas as pessoas físicas do sistema e encaminhar para o DtoResponse
     */
    @GetMapping
    public ResponseEntity<Page<VinculoDtoResponse>> find(
            @RequestParam(value = "q", defaultValue = "") String query,
            Pageable pageable) {
        Page<VinculoDtoResponse> vinculoDtoResponses = service.find(query, pageable)
                .map(vinculo -> modelMapper.map(vinculo, VinculoDtoResponse.class));
        return new ResponseEntity<>(vinculoDtoResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/cpf/{cpf}")
    public ResponseEntity<Page<VinculoDtoResponse>> findByCPFdoServidor(@PathVariable String cpf, Pageable pageable)throws IOException, TimeoutException{
        Page<VinculoDtoResponse> vinculoDtoResponses = service.findByCPFdoServidor(cpf, pageable)
                .map(vinculo -> modelMapper.map(vinculo, VinculoDtoResponse.class));


        //rabbitmqstuff();
        //return rabbitmqconsume(vinculoDtoResponses);


        return new ResponseEntity<>(vinculoDtoResponses, HttpStatus.OK);
    }

    public void rabbitmqstuff() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel();

            channel.queueDeclare("myvoting_queue",true,false,false,null);

            channel.basicPublish("", "myvoting_queue", null, ("TESTE FILA").getBytes(StandardCharsets.UTF_8));

        }
    }

    public ResponseEntity<Page<VinculoDtoResponse>> rabbitmqconsume(Page<VinculoDtoResponse> vinculoDtoResponses) throws IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel();

            channel.queueDeclare("myvoting_queue",false,false,false,null);

            //channel.queueBind("myvoting_queue","zz","");
            //channel.basicPublish("", "myvoting_queue", null, ("TESTE FILA").getBytes(StandardCharsets.UTF_8));

            /*
            channel.basicConsume("myvoting_queue",true,(consumerTag, message) -> {
                String m = new String(message.getBody(),"UTF-8");
                System.out.println(m);
                System.out.println(consumerTag);
                System.out.println("teste");
            },consumerTag -> {
                System.out.println(consumerTag);
            });
            */
            CompletableFuture.supplyAsync(()-> {
                return new ResponseEntity<>(vinculoDtoResponses, HttpStatus.FORBIDDEN);
            });
            return new ResponseEntity<>(vinculoDtoResponses, HttpStatus.OK);
            /*
            channel.basicConsume("myvoting_queue",true,"myconsumer",
                new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        //super.handleDelivery(consumerTag, envelope, properties, body);

                        System.out.println(new String(body,"UTF-8"));
                        long deliveryTag = envelope.getDeliveryTag();
                        System.out.println("KKJ");
                        channel.basicAck(deliveryTag, true);
                    }
                }
            );

             */

        }
    }

    @GetMapping(path = "async")
    public CompletableFuture<String> getAll(Pageable pageable){

        final String[] k = new String[1];
        ConnectionFactory factory = new ConnectionFactory();
        Instant start = Instant.now();

        return CompletableFuture.supplyAsync(() -> {
            while(k[0] == null) {
                try (Connection connection = factory.newConnection()) {
                    Channel channel = connection.createChannel();


                    channel.queueDeclare("myvoting_queue", true, false, false, null);

                    channel.basicConsume("myvoting_queue", true, (consumerTag, message) -> {
                        String m = new String(message.getBody(), "UTF-8");
                        k[0] = m;
                        System.out.println(m);
                        System.out.println(consumerTag);
                        System.out.println("teste");

                    }, consumerTag -> {
                        System.out.println(consumerTag);
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

                Instant end = Instant.now();
                if(Duration.between(start,end).toSeconds() > 30){
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(k[0] != null) {
                    break;
                    //return k[0];
                }
            }

            return k[0];
        });
    }

    @GetMapping(path = "/nome/{nome}")
    public ResponseEntity<Page<VinculoDtoResponse>> findByNomeDoServidor(@PathVariable String nome, Pageable pageable) throws IOException, TimeoutException {
        Page<VinculoDtoResponse> vinculoDtoResponses = service.findByNomeDoServidor(nome, pageable)
                .map(vinculo -> modelMapper.map(vinculo, VinculoDtoResponse.class));

        rabbitmqstuff();
        socketService.sendMessage();
        return new ResponseEntity<>(vinculoDtoResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/matricula/{matricula}")
    public ResponseEntity<Page<VinculoDtoResponse>> findByMatriculaDoServidor(@PathVariable String matricula, Pageable pageable){
        Page<VinculoDtoResponse> vinculoDtoResponses = service.findByMatriculaDoServidor(matricula, pageable)
                .map(vinculo -> modelMapper.map(vinculo, VinculoDtoResponse.class));
        return new ResponseEntity<>(vinculoDtoResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/eventosPermitidos/{idVinculo}")
    public ResponseEntity<Set<String>> buscarEventosPermitidos(@PathVariable Long idVinculo){
        Set<String> eventosPermitidos = service.buscarEventosPermitidos(idVinculo);
        return new ResponseEntity<>(eventosPermitidos, HttpStatus.OK);
    }
}
