import axios from 'axios';
import { useEffect,useState } from 'react';

function Dashboard() {

  const [email, setEmail] = useState('');
  useEffect(() => {
    getEmail();
  }, []);

  const  getEmail = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/users/email',{
  withCredentials: true
});
      setEmail(response.data);
    } catch (error) {

    }
  }

  return (
    <div className="dashboard-container">
      <h2>Dashboard</h2>
      <p>Bienvenue sur votre tableau de bord {email}!</p>
    </div>
  );
}

export default Dashboard;
