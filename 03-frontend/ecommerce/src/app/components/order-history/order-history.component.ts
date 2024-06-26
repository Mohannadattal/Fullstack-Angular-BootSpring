import { Component, OnInit } from '@angular/core';
import { OrderHistoryService } from '../../services/order-history.service';
import { OrderHistory } from '../../common/order-history';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrl: './order-history.component.css',
})
export class OrderHistoryComponent implements OnInit {
  orderHistoryList: OrderHistory[] = [];
  storage: Storage = sessionStorage;

  constructor(private orderHistory: OrderHistoryService) {}

  ngOnInit(): void {
    this.handleOrderHistory();
  }
  handleOrderHistory() {
    // read the user's email address from browser storage
    const email = JSON.parse(this.storage.getItem('userEmail')!);

    // retrieve data from the service
    this.orderHistory.getOrderHistory(email).subscribe((data) => {
      this.orderHistoryList = data._embedded.orders;
    });
  }
}
