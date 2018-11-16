import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecordItem } from 'app/shared/model/record-item.model';

@Component({
    selector: 'jhi-record-item-detail',
    templateUrl: './record-item-detail.component.html'
})
export class RecordItemDetailComponent implements OnInit {
    recordItem: IRecordItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recordItem }) => {
            this.recordItem = recordItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
