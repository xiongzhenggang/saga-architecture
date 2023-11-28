package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.command.Command;
import com.xzg.orchestrator.kit.command.CommandWithDestination;

import java.util.function.Function;
import java.util.function.Predicate;

public interface WithCompensationBuilder<Data> {

  InvokeParticipantStepBuilder<Data> withCompensation(Function<Data, CommandWithDestination> compensation);

  InvokeParticipantStepBuilder<Data> withCompensation(Predicate<Data> compensationPredicate, Function<Data, CommandWithDestination> compensation);

  <C extends Command> InvokeParticipantStepBuilder<Data> withCompensation(CommandEndpoint<C> commandEndpoint, Function<Data, C> commandProvider);

  <C extends Command> InvokeParticipantStepBuilder<Data> withCompensation(Predicate<Data> compensationPredicate, CommandEndpoint<C> commandEndpoint, Function<Data, C> commandProvider);
}
