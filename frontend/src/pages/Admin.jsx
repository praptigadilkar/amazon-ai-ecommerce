import { useNavigate } from "react-router-dom";

function Admin() {

    const navigate = useNavigate();

    return (

        <div style={styles.page}>

            <div style={styles.overlay}>

                <h1 style={styles.title}>
                    🛒 Amazon AI Admin Dashboard
                </h1>

                <p style={styles.subtitle}>
                    Manage your intelligent e-commerce platform
                </p>

                <div style={styles.container}>

                    <div
                        style={styles.card}
                        onClick={() => navigate("/products")}
                    >
                        <div style={styles.icon}>📦</div>
                        <h2>Products</h2>
                        <p>View and manage all products</p>
                    </div>

                    <div
                        style={styles.card}
                        onClick={() => navigate("/orders")}
                    >
                        <div style={styles.icon}>🛒</div>
                        <h2>Orders</h2>
                        <p>Track customer orders</p>
                    </div>

                    <div
                        style={styles.card}
                        onClick={() => navigate("/users")}
                    >
                        <div style={styles.icon}>👤</div>
                        <h2>Users</h2>
                        <p>Manage registered users</p>
                    </div>

                    <div
                        style={styles.card}
                        onClick={() => navigate("/cart")}
                    >
                        <div style={styles.icon}>🛍️</div>
                        <h2>Cart</h2>
                        <p>View shopping cart</p>
                    </div>

                </div>

            </div>

        </div>

    );
}

const styles = {

    page: {
        minHeight: "100vh",
        background: "linear-gradient(135deg,#131921,#232f3e,#ff9900)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: "40px"
    },

    overlay: {
        width: "100%",
        maxWidth: "1200px"
    },

    title: {
        color: "white",
        textAlign: "center",
        fontSize: "48px",
        marginBottom: "10px",
        fontWeight: "bold"
    },

    subtitle: {
        color: "#f3f3f3",
        textAlign: "center",
        fontSize: "20px",
        marginBottom: "50px"
    },

    container: {
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit,minmax(260px,1fr))",
        gap: "30px"
    },

    card: {
        background: "rgba(255,255,255,0.95)",
        borderRadius: "20px",
        padding: "35px",
        textAlign: "center",
        cursor: "pointer",
        transition: "0.3s",
        boxShadow: "0 10px 25px rgba(0,0,0,0.25)"
    },

    icon: {
        fontSize: "60px",
        marginBottom: "20px"
    }

};

export default Admin;