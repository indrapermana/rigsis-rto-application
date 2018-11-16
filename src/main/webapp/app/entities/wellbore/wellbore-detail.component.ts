import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWellbore } from 'app/shared/model/wellbore.model';

@Component({
    selector: 'jhi-wellbore-detail',
    templateUrl: './wellbore-detail.component.html'
})
export class WellboreDetailComponent implements OnInit {
    wellbore: IWellbore;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wellbore }) => {
            this.wellbore = wellbore;
        });
    }

    previousState() {
        window.history.back();
    }
}
