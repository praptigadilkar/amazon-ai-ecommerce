
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = async (e) => {
        e.preventDefault();

        try {
            const response = await api.post("/auth/login", {
                email,
                password
            });

            if (response.data.token) {
                localStorage.setItem("token", response.data.token);
                localStorage.setItem("userId", response.data.userId);
                localStorage.setItem("role", response.data.role);

                alert("Login successful!");
                navigate("/products");
            } else {
                alert("Invalid email or password");
            }
        } catch (error) {
            console.error("Login error:", error.response?.data || error.message);
            alert(
                error.response?.data?.message ||
                "Login failed. Please check your email and password."
            );
        }
    };

    return (
        <div style={styles.page}>
            <div style={styles.card}>
                <h1 style={styles.logo}>Amazon AI</h1>

                <p style={styles.subtitle}>
                    Intelligent Shopping Experience
                </p>

                <form onSubmit={login}>
                    <input
                        style={styles.input}
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />

                    <input
                        style={styles.input}
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <button type="submit" style={styles.button}>
                        Login
                    </button>
                </form>

                <p style={styles.bottom}>
                    Welcome back to AI Shopping 🚀
                </p>

                <p style={styles.registerText}>
                    New to Amazon AI?{" "}
                    <span
                        onClick={() => navigate("/signup")}
                        style={styles.registerLink}
                    >
                        Create your account
                    </span>
                </p>
            </div>
        </div>
    );
}

const styles = {
    page: {
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: "20px",
        boxSizing: "border-box",
        background: "linear-gradient(135deg, #232f3e, #ff9900)"
    },

    card: {
        width: "100%",
        maxWidth: "380px",
        padding: "40px",
        background: "#fff",
        borderRadius: "15px",
        boxShadow: "0 10px 30px rgba(0,0,0,0.3)",
        textAlign: "center",
        boxSizing: "border-box"
    },

    logo: {
        color: "#ff9900",
        fontSize: "38px",
        marginBottom: "5px"
    },

    subtitle: {
        color: "#555",
        marginBottom: "30px"
    },

    input: {
        width: "100%",
        padding: "14px",
        margin: "12px 0",
        borderRadius: "8px",
        border: "1px solid #ccc",
        fontSize: "16px",
        boxSizing: "border-box"
    },

    button: {
        width: "100%",
        padding: "14px",
        marginTop: "15px",
        border: "none",
        borderRadius: "8px",
        background: "#ff9900",
        color: "white",
        fontSize: "18px",
        cursor: "pointer"
    },

    bottom: {
        marginTop: "20px",
        color: "#777"
    },

    registerText: {
        marginTop: "18px",
        color: "#555",
        fontSize: "14px"
    },

    registerLink: {
        color: "#ff9900",
        fontWeight: "bold",
        cursor: "pointer"
    }
};

export default Login;
