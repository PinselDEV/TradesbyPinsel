package de.pinsel.tradesbypinsel.db;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.*;

public class TradeRepository {

    private final JavaPlugin plugin;
    private Connection connection;

    public TradeRepository(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) dataFolder.mkdirs();

            File dbFile = new File(dataFolder, "trades.db");
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

            connection = DriverManager.getConnection(url);

            try (Statement st = connection.createStatement()) {
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS listings (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      seller_uuid TEXT NOT NULL,
                      seller_name TEXT NOT NULL,
                      price REAL NOT NULL,
                      item_base64 TEXT NOT NULL,
                      created_at INTEGER NOT NULL
                    )
                """);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("[TradesbyPinsel] DB init failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException ignored) {}
    }

    public long addListing(UUID sellerUuid, String sellerName, double price, String itemBase64) {
        String sql = "INSERT INTO listings(seller_uuid, seller_name, price, item_base64, created_at) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sellerUuid.toString());
            ps.setString(2, sellerName);
            ps.setDouble(3, price);
            ps.setString(4, itemBase64);
            ps.setLong(5, System.currentTimeMillis());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public record Listing(long id, UUID sellerUuid, String sellerName, double price, String itemBase64) {}

    public List<Listing> getLatest(int limit, int offset) {
        List<Listing> list = new ArrayList<>();
        String sql = "SELECT id, seller_uuid, seller_name, price, item_base64 FROM listings ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    UUID uuid = UUID.fromString(rs.getString("seller_uuid"));
                    String name = rs.getString("seller_name");
                    double price = rs.getDouble("price");
                    String b64 = rs.getString("item_base64");
                    list.add(new Listing(id, uuid, name, price, b64));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Listing getById(long id) {
        String sql = "SELECT id, seller_uuid, seller_name, price, item_base64 FROM listings WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new Listing(
                        rs.getLong("id"),
                        UUID.fromString(rs.getString("seller_uuid")),
                        rs.getString("seller_name"),
                        rs.getDouble("price"),
                        rs.getString("item_base64")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM listings WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}