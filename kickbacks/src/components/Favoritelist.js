import React, { Component } from 'react'; 
import Favoriteitem from './Favoriteitem';
class Favoritelist extends Component {
  state = {}
  render() { 
    return ( <div className="result-column2" style={{width:"50%", height: "500px",overflowy: "scroll"}}>
      {
        this.props.favorites.map((data, index) => {
      return <Favoriteitem
        data={data}
        key={index}
        deleteFav={this.props.deleteFav}  
        />
        })}
      </div> )
  }
}
 
export default Favoritelist;