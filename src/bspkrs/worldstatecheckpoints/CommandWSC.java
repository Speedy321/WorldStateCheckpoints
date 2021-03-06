package bspkrs.worldstatecheckpoints;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.StatCollector;

public class CommandWSC extends CommandBase
{
    
    @Override
    public String getCommandName()
    {
        return "wsc";
    }
    
    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "commands.wsc.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender)
    {
        try
        {
            return Minecraft.getMinecraft().isSingleplayer();
        }
        catch (Throwable e)
        {
            return false;
        }
    }
    
    @Override
    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        if (args.length >= 1)
        {
            if (args[0].equalsIgnoreCase("save"))
            {
                String name = "";
                for (int i = 1; i < args.length; i++)
                    name += " " + args[i];
                if (name.trim().endsWith("!") || name.trim().endsWith("."))
                {
                    throw new WrongUsageException("commands.wsc.load.usage");
                }
                WSCSettings.cpm.setCheckpoint(name.trim(), name.trim().isEmpty() || name.contains(CheckpointManager.AUTOSAVES_PREFIX));
                return;
            }
            else if (args[0].equalsIgnoreCase("load"))
            {
                if (args.length >= 2)
                {
                    String name = "";
                    for (int i = 1; i < args.length; i++)
                        name += " " + args[i];
                    
                    String dirName = WSCSettings.cpm.getCheckpointDirNameFromDisplayName(name.trim());
                    if (dirName != null)
                        WSCSettings.delayedLoadCheckpoint(dirName, dirName.contains(CheckpointManager.AUTOSAVES_PREFIX), 1);
                    else
                        WSCSettings.mc.thePlayer.addChatMessage(StatCollector.translateToLocalFormatted("wsc.chatMessage.invalidCheckpointNameForLoadCommand", name.trim()));
                    
                    return;
                }
                else
                    throw new WrongUsageException("commands.wsc.load.usage");
            }
        }
        
        throw new WrongUsageException("commands.wsc.usage", new Object[0]);
    }
}
