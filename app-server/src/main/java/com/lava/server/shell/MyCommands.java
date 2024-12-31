package com.lava.server.shell;

import com.lava.server.customer.model.entity.Customer;
import com.lava.utils.autoconfigure.Ldbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.expression.ValueEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.shell.Availability;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Command
class MyCommands {

    @Autowired
    Ldbc ldbc;

    @Autowired
    CarPark carPark;



    @Command(command = "dsl")
    public String dsl() {
        return "carPark.getName()";
    }


    @Command(command = "hi")
    public String example(@Option(longNames = {"name"}) String name) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        /*Map<String, Object> data = new HashMap<String, Object>();
        data.put("lim", 1);*/
        //System.out.println(ldbc.run(name,carPark,"test"));
        //System.out.println(ldbc.run(name,carPark,p->"'name,family'",p->p.getTest2()));
        List<Customer> customers = ldbc.run(name, Customer.class, carPark, p -> "'name,family'", p -> p.getTest2());
        System.out.println(customers);
        return "Ldbc!";
    }

    @Bean
    public CommandRegistration zeroOrOneWithMinMax(
            CommandRegistration.BuilderSupplier builder) {
        return builder.get()
                .command("example")
                .withOption()
                .longNames("arg")
                .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
                .nameModifier(name -> "x" + name)
                .and()
                .withTarget()
                .consumer(ctx -> {
                    ctx.getTerminal().writer().println(ctx.getOptionValue("xarg").toString());

                })
                .and()
                .build();
    }

    private boolean connected;

    @Bean
    public CommandRegistration connect(
            CommandRegistration.BuilderSupplier builder) {
        return builder.get()
                .command("connect")
                .withOption()
                .longNames("connected")
                .required()
                .type(boolean.class)
                .and()
                .withTarget()
                .consumer(ctx -> {
                    boolean connected = ctx.getOptionValue("connected");
                    this.connected = connected;
                    ctx.getTerminal().writer().println("hi connected!");
                    ctx.getTerminal().writer().flush();
                })
                .and()
                .build();
    }

    @Bean
    public CommandRegistration download(
            CommandRegistration.BuilderSupplier builder) {
        return builder.get()
                .command("download")
                .availability(() -> {
                    return connected
                            ? Availability.available()
                            : Availability.unavailable("you are not connected");
                })
                .withAlias()
                .command("d")
                .and()
                .withTarget()
                .consumer(ctx -> {
                    // do something
                })
                .and()
                .build();
    }
}