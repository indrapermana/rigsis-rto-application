import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecordItem } from 'app/shared/model/record-item.model';
import { Principal } from 'app/core';
import { RecordItemService } from './record-item.service';

@Component({
    selector: 'jhi-record-item',
    templateUrl: './record-item.component.html'
})
export class RecordItemComponent implements OnInit, OnDestroy {
    recordItems: IRecordItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private recordItemService: RecordItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.recordItemService.query().subscribe(
            (res: HttpResponse<IRecordItem[]>) => {
                this.recordItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecordItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecordItem) {
        return item.id;
    }

    registerChangeInRecordItems() {
        this.eventSubscriber = this.eventManager.subscribe('recordItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
