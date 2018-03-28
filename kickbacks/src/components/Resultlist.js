import React, { Component } from 'react';
import Result from './Result'
class Resultlist extends Component {
  state = {}
  render() { 
    return (<div>
              {
              this.props.data.map((data, index) => {
                return <Result 
                  data={this.props.data} 
                  key={index} 
                           />
                })
              }
      
          </div>  )
  }
}
 
export default Resultlist;