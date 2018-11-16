import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnitTypeItem } from 'app/shared/model/unit-type-item.model';

@Component({
    selector: 'jhi-unit-type-item-detail',
    templateUrl: './unit-type-item-detail.component.html'
})
export class UnitTypeItemDetailComponent implements OnInit {
    unitTypeItem: IUnitTypeItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unitTypeItem }) => {
            this.unitTypeItem = unitTypeItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
