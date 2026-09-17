import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Cart() {

    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {

        loadCart();

    }, []);


    const loadCart = () => {

    const userId = localStorage.getItem("userId");

    api.get(`/cart?userId=${userId}`)
        .then((response) => {
            setCartItems(response.data);
        })
        .catch((error) => {
            console.log(error);
        });

};


    const removeItem = (cartId) => {

        api.delete(`/cart/${cartId}`)
            .then(() => {
                loadCart();
            })
            .catch((error) => {
                console.log(error);
            });

    };


   const placeOrder = () => {

    const userId = localStorage.getItem("userId");

    api.post(`/orders/place/${userId}`)
        .then(() => {

            alert("Order placed successfully 🛒");

            navigate("/orders");

        })
        .catch((error) => {
            console.log(error);
        });

};


    const total = cartItems.reduce(
        (sum, item) => sum + item.totalPrice,
        0
    );


    return (

        <div style={styles.page}>

            <h1 style={styles.title}>
                🛒 My Cart
            </h1>


            {
                cartItems.map((item)=>(

                    <div 
                        key={item.cartId}
                        style={styles.card}
                    >

                        <h2>
                            {item.productName}
                        </h2>


                        <p>
                            Quantity: {item.quantity}
                        </p>


                        <p>
                            Price: ₹ {item.productPrice}
                        </p>


                        <p>
                            Total: ₹ {item.totalPrice}
                        </p>


                        <button
                            style={styles.remove}
                            onClick={() => removeItem(item.cartId)}
                        >
                            Remove
                        </button>


                    </div>

                ))
            }



            <h2 style={styles.total}>
                Total Amount: ₹ {total}
            </h2>


            <button
                style={styles.orderButton}
                onClick={placeOrder}
            >
                Place Order
            </button>


        </div>

    );

}



const styles = {

    page:{
        minHeight:"100vh",
        background:"#eaeded",
        padding:"40px"
    },


    title:{
        textAlign:"center",
        color:"#131921",
        fontSize:"35px"
    },


    card:{
        background:"white",
        padding:"20px",
        margin:"20px auto",
        width:"400px",
        borderRadius:"12px",
        boxShadow:"0 4px 15px rgba(0,0,0,0.15)"
    },


    remove:{
        background:"#d9534f",
        color:"white",
        border:"none",
        padding:"10px 20px",
        borderRadius:"8px",
        cursor:"pointer"
    },


    total:{
        textAlign:"center",
        color:"#b12704"
    },


    orderButton:{
        display:"block",
        margin:"20px auto",
        background:"#ff9900",
        border:"none",
        padding:"15px 40px",
        borderRadius:"25px",
        cursor:"pointer",
        fontWeight:"bold",
        fontSize:"16px"
    }

};


export default Cart;