package com.sougata.springsecurityjwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtil {
    private String SECRET_KEY = "B^ap5KPe*_trycKXY3hzfDErp7r3?CFU7Ftnkj8MkYx!6VJ6wGvF*pZj8phhug" +
            "Ch^3N*^@VYH*++g*jXgJYrG#W7buU%XJ$ggyhMX76dv*JUGfUjnazQKcqMzDwSJSJQLd%&FJcDW_ktC#6N" +
            "y%fdv4MHS*y8nr@$S7@RLwUUqqeHUeQhJSkUm?JdWuQd3AV7k&mLw4&!dZ_#v&c5QBUCP64tJ7GGp!=E$?" +
            "m7tVhWLRx&J=Ap*YNQdmvEzRsdJ^Wx^%_%-&5VA!%h=TwhbwG&BjKc4nr-mLXKByQP5CGJAjdL@yrbq3rm" +
            "W_6#f3qh7tjEp_muZzL?TCb$xs*Sfg2VxXHuE8zxMar4C^Uhkhe!RR2pdGvCkv82ykVh3v=2GD+?9Eh4^H" +
            "5F=MXQQbSsyGadc8zkUZEM%m8$=LAr#%aMkPmfD-Bx*dP8Lk^4usFy4jFKB?--au#wCx_My3Z6xdym4m-4" +
            "7sxN@A7K+KjVHSgUEC5X@WTc4^6ZFr3dVavPwxWFvmM&xBBpWMw8FuP^-v=FU+&?bkVkEKe5J6=cHWK&VW" +
            "G9DEtYzrvmsY$f%J+cV-Fq2WBYVh%y8?4#tv+=8aww6xse5tYc=fSWfTJD2dA%^3erM$*#WyhG$+qAazWb" +
            "b8#Fg@vPm!eJ-U_WguVqhjFKdZ_HE%qbjZvH75M?7zjaCmjbF!2$MvccZ*e5&74_kpED5zV#X^L@TnAztj" +
            "LjPSCQtLD5z%nY*99udA63h@hZDuT-!%g=!xuLZHsUy4rDkWA&BnSw4T?#ZJ&mA_?Uv5C&^zATXn$aF58@" +
            "m4Jc*r%wb2Kw_Y#aJ9eJnKGtu?@WPA&Q_Z!^9!RAa_RP8uNp#TUE8@WUr2$Q9m^M6*W_tQ8*F#jvu7u5W$" +
            "PFvHHM&_CRFEGuPZLM$jG!!$EveCfH+?!*nUVm*Qxja6rX5Ng&mmcnBeE=*mYa8?FbdUTM9MSzb9r2V-r7" +
            "pRRNzFB%7*FT$+EcZR+pT^9Y&JNQ7Z+4nR9$CnkSaF7vKW2XZJEpaBn3qt9XyS2zQ8x2*xZ$Np3g-G2cu^" +
            "@CRjmdpd9zezQue%#Y#JSpT4yE5tfQ-%-r=dCGPA6_a7%ye5Zws%pw4h59QAJC_Y!4%8=hc69BNsRT$y?R" +
            "h2+-=7QQGWQz@c2cN9PSsgv!3gdHAXzL^=7Q%LjacVZz6#+6Rqr5%^nbY8J!LTp&mJEk2-qZb%?kXCdx@j" +
            "%JWH-SH38F=RLC^@rYHbK$eE=HU&6u&v+db_LvzsgY+zT?a4FA-%VyxQ-ue2zQX3%ADSB4jJ@yrPaCJ#bF" +
            "xXh3hM$-%_!JM#RTKL!X^rurz&EjbX_EVuUkqYum88v=$rTq$dy3*B_HxTzS*D*&F*b$KmPxABxf*9z#dP" +
            "*!RkzrEtBAXya?j8#5vHvdDPp3&!^_HszA4_YfZ5XLT!SgvK5#wDm_q!Ejgz$EXCm2@*Uj5Lrejj*7^YcE" +
            "ku6J%+E%U76^uzAn2y$*vjE?=Drw=HTJ&U_jcfX^8*+mAJNU2Zt&d?H%c2%GDkRzgnSe_aS5s8V#2x@KHF" +
            "gnbqa=!ab7tJNnGg89pZbTS9=*&Pe9e+_c@y&pVgTP!2h_6fvjV5d-MH%SHw_PZhJx-LnLuYFmy-QByx!K" +
            "2JKma4smh+hV4-nDG=XTUP7En+Ycc-Are=4m4CVBVS$-$=FN5XSA+Dj$-w$6cvJ^Er#z=4w2-R3C%BS6nY" +
            "YPjRJVHyN#^eWgf&93nn!L@H5CaSkn85fMbvh!@@R^jceF8dbnYXwkbCu2LkEF@u=au--6AwCU77$L22X%" +
            "yvyYcFSCqHtxm@UQF!x3YBRfrfEyFftHSggwhQXx+7%V#b$^RYZ+@&9Wnhb+vu?Eqb@V#wzK^rXV#c*HXJ" +
            "VYj!$XAR7v*WguSQfCqpR$nSNGXgR8VkNPh8f_HNhZF^6^76@8vty4VL#z3$_=%PDBDQH6q%netKZ2P%3R" +
            "vE$&-P4aBR3QEke!EP+rYQ-XM4CTtA2xTxC=D9w=bsu&@dkp3gh9RK$#p?WeVjKw_qrj#xb3QECJN2#d=qXZg9N_NJ#Aj7u9wHev";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername() + "_" + userDetails.getPassword());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String usernameAndPassword = extractUsername(token);
        return (usernameAndPassword.equals(userDetails.getUsername() + "_" + userDetails.getPassword())
                && !isTokenExpired(token));
    }
}
