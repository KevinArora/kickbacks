import React, { Component } from 'react';
class Favoriteitem extends Component {
  state = {}
  // handleClick = () => {
  //   console.log(this.props.data);
    
  //   let data = this.props.data
  //   this.props.deleteFav(data)
  // }
  handleClick = () => {
    console.log("click")
    
    let data = {
        ...this.props.data,
        index: this.props.index
    }

    this.props.deleteFav(data,1);
}
  render() { 
    return ( <div>
      <div onClick={this.handleClick} className="listitem">
                <p>{this.props.data.title}</p> 
                <p>{this.props.description}</p>
                
                <button className="savebutton" onClick={this.props.handleClick}>DELETE FROM LIST</button>
            </div>
    </div> )
  }
}
 
export default Favoriteitem;