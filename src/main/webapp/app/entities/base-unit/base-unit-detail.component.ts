import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBaseUnit } from 'app/shared/model/base-unit.model';

@Component({
    selector: 'jhi-base-unit-detail',
    templateUrl: './base-unit-detail.component.html'
})
export class BaseUnitDetailComponent implements OnInit {
    baseUnit: IBaseUnit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ baseUnit }) => {
            this.baseUnit = baseUnit;
        });
    }

    previousState() {
        window.history.back();
    }
}
