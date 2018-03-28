import React, { Component } from 'react';
import Result from './Result'
class Resultlist extends Component {
  state = {}
  render() {
    
      console.log('asdfasdfasdfasdfasdf', this.props.data)

    return (
      <div className="result-column" style={{width:"50%", height: "500px",overflowy: "scroll"}}>
        {
          this.props.data.map((data, index) => {
            return <Result 
                data={data} 
                key={index} 
              />
            })
          }
      
          </div>  )
  }
}
 
export default Resultlist;