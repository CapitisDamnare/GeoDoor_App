package tapsi.geodoor.retrofit.models;

public class CommandItem
{
    public AuthModel authentication;
    public CommandValue commandValue;
    public Command command;

    public enum Command
    {
        OpenDoor,
        OpenGate,
        OpenGateAuto
    }

    public enum CommandValue
    {
        Open,
        Close,
        ForceOpen,
        ForceClose
    }

    public enum CommandValueAnswer
    {
        AlreadyOpen,
        AlreadyClosed,
        GateOpening,
        GateClosing,
        AlreadyOpening,
        AlreadyClosing
    }
}
