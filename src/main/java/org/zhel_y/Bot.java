package org.zhel_y;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.BanChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    List<String> emodzi4Block = Arrays.asList("\uD83D\uDE0D",
            "\uD83D\uDC59",
            "\uD83D\uDCA6",
            "\uD83E\uDEE6",
            "\uD83D\uDC98",
            "\uD83D\uDC8B",
            "\uD83D\uDC57",
            "\uD83C\uDF53",
            "\uD83D\uDCA8");

    String[] sayWhore = new String[]{
            "Ах ты сучка ты крашена, ",
            "Иди ка ты отсоси кому-то другому, ",
            "Реализуйте, пожалуйста, право граждан на иррумацию в другом месте, ",
            "Настоящий минет делается не ртом, а сердцем дорогая, ",
            "И помыта и подбрита ждет тебя в постели: "
    };

    String[] sayBue = new String[] {
            ", пошла на хуй!",
            ", пиздуй ка от сюда, мразь!",
            ", в другом месте зарабатывай, чучело!",
            ", вали и расскажи подругам, чтоб не лазили сюда!",
            ", денег нет, проваливай!",
            ", пиздуй в бордель, у нас тут притон, дура!"
    };

    @Override
    public void onUpdateReceived(Update update) {
        List<User> memberList = update.getMessage().getNewChatMembers()==null ? null : update.getMessage().getNewChatMembers();
        Message message = update.getMessage();
        try {
            if (message.getLeftChatMember()!=null) deleteOutcome(message);
            if (memberList != null && !memberList.isEmpty()) SendToTheXYZ(memberList.get(0), update);
        } catch (TelegramApiException e) {
            try {
                e.printStackTrace();
                execute(SendMessage.builder().text("Сорян, я женщина и у меня не достаточно прав, так что... поприветствуем эту суку! (удалять теперь можно только ручками)").chatId(update.getMessage().getChatId()).build());
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void SendToTheXYZ(User user, Update update) throws TelegramApiException {
        if (isNeedSendToTheXYZ(user)) {
            BanChatMember banChatMember = new BanChatMember().builder()
                    .chatId(update.getMessage().getChatId().longValue())
                    .userId(user.getId())
                    .untilDate(0)
                    .revokeMessages(true)
                    .build();
            Random random = new Random();
            execute(SendMessage.builder().text(
                    sayWhore[random.nextInt(5)]
                            + returnWhoreName(user)
                            + sayBue[random.nextInt(6)])
                    .chatId(update.getMessage().getChatId()).build());
            execute(banChatMember);
            DeleteMessage income = new DeleteMessage();
            income.setChatId(update.getMessage().getChatId());
            income.setMessageId(update.getMessage().getMessageId());
            execute(income);
            execute(SendMessage.builder().text("В ЧАТЕ " + update.getMessage().getChat().getTitle() + ",  поймана шлюха").chatId("117060149").build());
        }
    }
    private boolean isNeedSendToTheXYZ(User user) {
        for (String s:
             emodzi4Block) {
            if (containsForbidden(user, s)) {
            return true;
            }
        }
        return false;
    }

    private boolean containsForbidden(User user, String s) {
        if (user.getFirstName() != null && user.getFirstName().contains(s)) return true;
        if (user.getLastName() != null && user.getLastName().contains(s)) return true;
        return user.getUserName() != null && user.getUserName().contains(s);
    }

    private String returnWhoreName(User user) {
        if (user.getFirstName() != null) return user.getFirstName();
        if (user.getLastName() != null) return user.getLastName();
        if (user.getUserName() != null) return user.getUserName();
        return "ШЛЮХА";
    }

    private void deleteOutcome(Message message) throws TelegramApiException {
        if (isNeedSendToTheXYZ(message.getLeftChatMember())) {
            DeleteMessage outcome = new DeleteMessage();
            outcome.setChatId(message.getChatId());
            outcome.setMessageId(message.getMessageId());
            execute(outcome);
        }
    }

    @Override
    public String getBotUsername() {
        return "Zhelybot";
    }
    @Override
    public String getBotToken() {
        return "6689844232:AAGN-WQ-ERmuWg9TDkfbLDr4K1TnATaiL_8";
    }
}
