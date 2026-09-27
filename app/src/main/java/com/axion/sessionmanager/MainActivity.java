// language: Java 17, file: MainActivity.java, target: Android 6.0+ / Android 15
package com.axion.sessionmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private final List<Session> sessions = new ArrayList<>();
    private int gold = Color.rgb(233, 185, 63);
    private int bg = Color.rgb(7, 10, 16);
    private int card = Color.rgb(14, 18, 26);
    private int text = Color.rgb(243, 244, 246);
    private int muted = Color.rgb(154, 163, 178);
    private int green = Color.rgb(121, 226, 166);

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        seed();
        buildUi();
    }

    private void seed() {
        sessions.add(new Session("04", "Xiaomi Redmi Note 13", "https://google.com", "Yogyakarta", "-7,7956", "110,3695"));
        sessions.add(new Session("05", "vivo V30", "https://google.com", "Surabaya", "-7,2575", "112,7521"));
        sessions.add(new Session("07", "Google Pixel 8", "https://google.com", "Makassar", "-5,1477", "119,4327"));
        sessions.add(new Session("08", "Oppo A2R", "https://google.com", "Denpasar", "-8,65", "115,2167"));
        sessions.add(new Session("10", "Infinix Note 40", "https://google.com", "Malang", "-7,9666", "112,6326"));
        sessions.add(new Session("11", "Infinix Note 40", "https://google.com", "Palembang", "-2,9761", "104,7754"));
        sessions.add(new Session("12", "Tecno Camon 30", "https://google.com", "Bandung", "-6,9175", "107,6191"));
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(12), dp(14), dp(12), dp(24));
        scroll.addView(root);

        TextView title = label("SESSION MANAGER", 12, muted);
        title.setLetterSpacing(.12f);
        root.addView(title, lp(-1, -2, 0, 0, 4, 0));

        TextView heading = label("Sessions", 27, text);
        heading.setTypeface(null, 1);
        root.addView(heading, lp(-1, -2, 0, 0, 10, 2));

        TextView sub = label("Kelola sesi perangkat dan buka URL pengujian.", 13, muted);
        root.addView(sub, lp(-1, -2, 0, 0, 0, 14));

        for (Session s : sessions) root.addView(makeCard(s), lp(-1, -2, 0, 0, 0, 12));
        setContentView(scroll);
    }

    private View makeCard(Session s) {
        LinearLayout cardBox = new LinearLayout(this);
        cardBox.setOrientation(LinearLayout.VERTICAL);
        cardBox.setPadding(dp(14), dp(13), dp(14), dp(13));
        cardBox.setBackgroundResource(com.axion.sessionmanager.R.drawable.card_bg);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        TextView tag = label(s.id, 10, Color.DKGRAY);
        tag.setGravity(Gravity.CENTER);
        tag.setBackgroundResource(com.axion.sessionmanager.R.drawable.gold_button);
        top.addView(tag, lp(dp(48), dp(25), 0, 0, 0, 0));
        TextView ready = label("READY", 9, green);
        LinearLayout.LayoutParams rp = lp(0, -2, 1, 0, 0, 0); rp.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        top.addView(ready, rp);
        cardBox.addView(top, lp(-1, -2, 0, 0, 0, 9));

        TextView device = label(s.device, 19, text);
        device.setTypeface(null, 1);
        cardBox.addView(device, lp(-1, -2, 0, 0, 0, 13));

        cardBox.addView(field("L A Y A R", "USER-AGENT"), lp(-1, -2, 0, 0, 0, 9));
        cardBox.addView(field("URL pengujian", s.url), lp(-1, -2, 0, 0, 0, 8));
        cardBox.addView(field("Kota", s.city), lp(-1, -2, 0, 0, 0, 9));

        LinearLayout coords = new LinearLayout(this);
        coords.setOrientation(LinearLayout.HORIZONTAL);
        coords.addView(field("Latitude", s.lat), lp(0, -2, 1, 0, 0, 0));
        coords.addView(field("Longitude", s.lon), lp(0, -2, 1, dp(12), 0, 0));
        cardBox.addView(coords, lp(-1, -2, 0, 0, 0, 12));

        LinearLayout actions = new LinearLayout(this);
        actions.setGravity(Gravity.CENTER_VERTICAL);
        Button open = button("Buka Sesi", true);
        open.setOnClickListener(v -> openUrl(s.url));
        actions.addView(open, lp(0, dp(43), 1, 0, 0, 0));
        Button save = button("Simpan", false);
        save.setOnClickListener(v -> {
            s.saved = !s.saved;
            save.setText(s.saved ? "Tersimpan" : "Simpan");
            Toast.makeText(this, s.saved ? "Sesi disimpan" : "Sesi dihapus dari simpanan", Toast.LENGTH_SHORT).show();
        });
        actions.addView(save, lp(dp(76), dp(43), 0, dp(7), 0, 0));
        cardBox.addView(actions, lp(-1, dp(43), 0, 0, 0, 0));
        return cardBox;
    }

    private LinearLayout field(String name, String value) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        TextView n = label(name, 9, muted); n.setLetterSpacing(.04f);
        TextView v = label(value, 13, text); v.setTypeface(null, 1);
        box.setPadding(dp(0), dp(0), dp(0), dp(0));
        box.addView(n, lp(-1, -2, 0, 0, 0, 3));
        box.addView(v, lp(-1, -2, 0, 0, 0, 0));
        return box;
    }

    private Button button(String s, boolean primary) {
        Button b = new Button(this);
        b.setText(s); b.setTextSize(11); b.setAllCaps(false); b.setTextColor(primary ? Color.rgb(20,20,20) : text);
        b.setBackgroundResource(primary ? R.drawable.gold_button : R.drawable.dark_button);
        return b;
    }

    private TextView label(String s, int size, int color) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); return t;
    }

    private void openUrl(String url) {
        try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); }
        catch (Exception e) { Toast.makeText(this, "Tidak ada aplikasi browser", Toast.LENGTH_SHORT).show(); }
    }

    private LinearLayout.LayoutParams lp(int w, int h, float weight, int l, int t, int b) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w == -1 ? -1 : (w == 0 ? 0 : w), h == -1 ? -1 : (h == 0 ? 0 : h), weight);
        p.setMargins(l, t, 0, b); return p;
    }
    private int dp(int v) { return Math.round(v * getResources().getDisplayMetrics().density); }

    static class Session {
        String id, device, url, city, lat, lon; boolean saved;
        Session(String id, String device, String url, String city, String lat, String lon) { this.id=id; this.device=device; this.url=url; this.city=city; this.lat=lat; this.lon=lon; }
    }
}
