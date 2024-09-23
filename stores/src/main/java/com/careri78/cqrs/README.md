An implementation of the CQRS pattern.

Uses reflection under the hood to figure out which handler should go to which query/command.
As a user you need to add the handlers as beans of the ApplicationContext.

*TODO* Move to it's own package/project.