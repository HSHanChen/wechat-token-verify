package com.chansheen.wechat;

import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.util.Arrays;

@RestController
@RequestMapping("/wechat")
public class WeChatTokenController {

    private final WeChatConfig weChatConfig;

    public WeChatTokenController(WeChatConfig weChatConfig) {
        this.weChatConfig = weChatConfig;
    }

    /**
     * 微信公众号 Token 验证
     */
    @GetMapping("/verify")
    public String verifyWeChatToken(@RequestParam String signature,
        @RequestParam String timestamp,
        @RequestParam String nonce,
        @RequestParam String echostr) {
        if (checkSignature(signature, timestamp, nonce)) {
            return echostr; // 认证成功，返回echostr
        } else {
            return "Invalid request"; // 认证失败
        }
    }

    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String token = weChatConfig.getToken();
        String[] array = { token, timestamp, nonce };
        Arrays.sort(array);
        String content = array[0] + array[1] + array[2];

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,3));
            }
            return signature.equals(hexString.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 处理加密消息（安全模式）
     */
    @PostMapping("/message")
    public String handleWeChatMessage(@RequestBody String encryptedXml,
        @RequestParam String msg_signature,
        @RequestParam String timestamp,
        @RequestParam String nonce) {
        try {
            String decryptedMessage = WeChatSecurityUtil.decryptMessage(encryptedXml, weChatConfig.getEncodingAesKey());
            return "Received: " + decryptedMessage;
        } catch (Exception e) {
            return "Decryption error: " + e.getMessage();
        }
    }
}
