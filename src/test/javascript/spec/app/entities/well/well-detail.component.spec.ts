/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { WellDetailComponent } from 'app/entities/well/well-detail.component';
import { Well } from 'app/shared/model/well.model';

describe('Component Tests', () => {
    describe('Well Management Detail Component', () => {
        let comp: WellDetailComponent;
        let fixture: ComponentFixture<WellDetailComponent>;
        const route = ({ data: of({ well: new Well(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WellDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WellDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WellDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.well).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
