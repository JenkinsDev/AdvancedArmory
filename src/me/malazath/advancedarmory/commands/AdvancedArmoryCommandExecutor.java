package me.malazath.advancedarmory.commands;

import me.malazath.advancedarmory.AdvancedArmory;
import me.malazath.advancedarmory.commands.command.GiveCustomItemCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdvancedArmoryCommandExecutor implements CommandExecutor
{

	private static AdvancedArmory advancedArmory;

	public AdvancedArmoryCommandExecutor(AdvancedArmory plugin)
	{
		advancedArmory = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		if (! command.getName().equalsIgnoreCase("AdvancedArmory"))
			return false;

		if (! isSenderPlayer(sender)) {
			sender.sendMessage(ChatColor.RED + "You cannot send the " + commandLabel + " command via the console.");
			return false;
		}

		Player senderPlayer = (Player) sender;

		if (args.length <= 1 || ! args[0].equalsIgnoreCase("give")) {
			invalidCommand(senderPlayer);
			return false;
		}

		if (! advancedArmory.doesPlayerHavePermission(senderPlayer, "advancedarmory.*") && ! advancedArmory.doesPlayerHavePermission(senderPlayer, "advancedarmory.give")) {
			advancedArmory.doesNotHavePermission(senderPlayer);
			return false;
		}

		String item = args[1];

		if (args.length == 2) {
			// If only one argument is passed then we will guess that the receiver is the CommandSender
			instantiateGiveCommand(senderPlayer, item, senderPlayer);
		} else if (args.length == 3) {
			Player receiver = advancedArmory.getPlayerByName(args[2]);

			if (receiver == null) {
				sender.sendMessage(ChatColor.RED + "That player doesn't seem to be online at this time.");
				return false;
			}

			instantiateGiveCommand(senderPlayer, item, receiver);
		}

		return true;
	}

	private boolean isSenderPlayer(CommandSender sender)
	{
		return sender instanceof Player;
	}

	private void invalidCommand(Player sender)
	{
		sender.sendMessage(ChatColor.RED + "AdvancedArmory: Invalid Command");
		sender.sendMessage(ChatColor.RED + "====== Commands ======");
		sender.sendMessage(ChatColor.GREEN + "/aa give <item> [player]");
		//sender.sendMessage(ChatColor.GREEN + "/aa reload");
	}

	private void instantiateGiveCommand(Player sender, String item, Player receiver)
	{
		new GiveCustomItemCommand(sender, item, receiver, advancedArmory);
	}

	// Will be used in a future release of AdvancedArmory, will handle calling the need to reload the config.yml
	public void reloadCommand() { }

}
